import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { EmploymentAppDetailComponent } from 'app/entities/employment-app/employment-app-detail.component';
import { EmploymentApp } from 'app/shared/model/employment-app.model';

describe('Component Tests', () => {
  describe('EmploymentApp Management Detail Component', () => {
    let comp: EmploymentAppDetailComponent;
    let fixture: ComponentFixture<EmploymentAppDetailComponent>;
    const route = ({ data: of({ employmentApp: new EmploymentApp(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmploymentAppDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EmploymentAppDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmploymentAppDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load employmentApp on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.employmentApp).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
