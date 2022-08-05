import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { UomDetailComponent } from 'app/entities/uom/uom-detail.component';
import { Uom } from 'app/shared/model/uom.model';

describe('Component Tests', () => {
  describe('Uom Management Detail Component', () => {
    let comp: UomDetailComponent;
    let fixture: ComponentFixture<UomDetailComponent>;
    const route = ({ data: of({ uom: new Uom(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [UomDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UomDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UomDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load uom on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.uom).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
