import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { DeductionTypeDetailComponent } from 'app/entities/deduction-type/deduction-type-detail.component';
import { DeductionType } from 'app/shared/model/deduction-type.model';

describe('Component Tests', () => {
  describe('DeductionType Management Detail Component', () => {
    let comp: DeductionTypeDetailComponent;
    let fixture: ComponentFixture<DeductionTypeDetailComponent>;
    const route = ({ data: of({ deductionType: new DeductionType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [DeductionTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DeductionTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DeductionTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load deductionType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.deductionType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
