import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { EmploymentAppSourceTypeDetailComponent } from 'app/entities/employment-app-source-type/employment-app-source-type-detail.component';
import { EmploymentAppSourceType } from 'app/shared/model/employment-app-source-type.model';

describe('Component Tests', () => {
  describe('EmploymentAppSourceType Management Detail Component', () => {
    let comp: EmploymentAppSourceTypeDetailComponent;
    let fixture: ComponentFixture<EmploymentAppSourceTypeDetailComponent>;
    const route = ({ data: of({ employmentAppSourceType: new EmploymentAppSourceType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmploymentAppSourceTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EmploymentAppSourceTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmploymentAppSourceTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load employmentAppSourceType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.employmentAppSourceType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
